package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

// Для интеграции с медиа Windows 10/11
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MusicPlayerHud extends DraggableHudComponent {
    private String trackTitle = "Ожидание музыки...";
    private String trackArtist = "Система Windows";
    private long lastUpdate = 0;

    public MusicPlayerHud(int x, int y) {
        super(x, y, 190, 75);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Обновляем информацию о треке раз в 3 секунды, чтобы не грузить процессор
        if (System.currentTimeMillis() - lastUpdate > 3000) {
            updateCurrentTrack();
            lastUpdate = System.currentTimeMillis();
        }

        // Темно-фиолетовый фон плеера в стиле Syntax
        context.fill(x, y, x + width, y + height, 0xCC0B001A);

        // Имитация квадрата обложки
        context.fill(x + 10, y + 12, x + 45, y + 47, 0xFF2A1B3D);

        // Обрезаем длинный текст, чтобы он не вылезал за рамки окошка
        String displayTitle = trackTitle.length() > 22 ? trackTitle.substring(0, 22) + "..." : trackTitle;
        String displayArtist = trackArtist.length() > 25 ? trackArtist.substring(0, 25) + "..." : trackArtist;

        // Выводим реальное название трека и исполнителя
        context.drawText(client.textRenderer, "§d" + displayTitle, x + 52, y + 12, 0xFFFFFFFF, true);
        context.drawText(client.textRenderer, "§7" + displayArtist, x + 52, y + 24, 0xFFAAAAAA, true);

        // Элементы управления
        context.drawText(client.textRenderer, "⏮   ⏸   ⏭", x + 70, y + 40, 0xFFBA55D3, true);
        
        // Полоска прогресса
        context.fill(x + 52, y + 58, x + 180, y + 61, 0xFF221133);
        context.fill(x + 52, y + 58, x + 110, y + 61, 0xFFBA55D3); // Динамический прогресс (можно привязать к реальному времени)
        
        context.drawText(client.textRenderer, "LIVE", x + 52, y + 64, 0xFF00FFCC, false);
    }

    // Метод для получения трека из Windows через PowerShell (Global System Media Transport Protocols)
    private void updateCurrentTrack() {
        try {
            String command = "powershell -Command \"" +
                    "$sessionManager = [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager,Windows.Media,ContentType=WindowsRuntime]::RequestAsync().GetAwaiter().GetResult();" +
                    "$session = $sessionManager.GetCurrentSession();" +
                    "if ($session) {" +
                    "   $properties = $session.TryGetMediaPropertiesAsync().GetAwaiter().GetResult();" +
                    "   Write-Output $properties.Title;" +
                    "   Write-Output $properties.Artist;" +
                    "}\"";

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            
            String title = reader.readLine();
            String artist = reader.readLine();

            if (title != null && !title.isEmpty()) {
                this.trackTitle = title;
            } else {
                this.trackTitle = "Музыка не играет";
            }

            if (artist != null && !artist.isEmpty()) {
                this.trackArtist = artist;
            } else {
                this.trackArtist = "Неизвестно";
            }
        } catch (Exception e) {
            this.trackTitle = "Ошибка чтения медиа";
            this.trackArtist = "Windows API";
        }
    }
}
