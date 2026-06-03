package me.alpha432.oyvey.features.gui.components.items.buttons;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.gui.ClickGuiScreen;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.RenderUtil;
import java.awt.Color;

public class ModuleButton extends Button {
    private final Module module;
    
    // Variablen für den flüssigen "itsme"-Look
    private float hoverAnimation = 0f;

    public ModuleButton(Module module) {
        super(module.getName());
        this.module = module;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // 1. Berechnen, ob die Maus über dem Button schwebt
        boolean isHovered = isHovering(mouseX, mouseY);
        
        // Linear Interpolation für smoothes Ein-/Ausfaden (Delta-Berechnung)
        float target = isHovered ? 1f : 0f;
        float diff = target - hoverAnimation;
        hoverAnimation += diff * 0.2f; // 0.2f bestimmt die Geschwindigkeit der Animation

        // 2. Cleane, moderne Farben definieren
        // Wenn das Modul an ist -> Dunkles Lila/Blau, sonst Anthrazit
        Color baseColor = module.isEnabled() ? new Color(45, 45, 65, 230) : new Color(25, 25, 25, 200);
        // Sanfter Cyan-Schimmer beim Hovern
        Color hoverColor = new Color(0, 190, 255, (int)(hoverAnimation * 60)); 

        // 3. GUI-Elemente zeichnen
        // Hintergrund
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, baseColor.getRGB());
        
        // Hover-Glow drüberblenden
        if (hoverAnimation > 0) {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, hoverColor.getRGB());
        }

        // Eine feine, leuchtende vertikale Linie links, wenn das Modul aktiv ist
        if (module.isEnabled()) {
            RenderUtil.drawRect(this.x, this.y, this.x + 2, this.y + this.height, new Color(0, 200, 255).getRGB());
        }

        // 4. Text zeichnen (rutscht beim Hovern smooth um 2 Pixel nach rechts)
        OyVey.textManager.drawStringWithShadow(
            module.getName(), 
            this.x + 5 + (hoverAnimation * 2f), 
            this.y + (this.height / 2f) - (OyVey.textManager.getFontHeight() / 2f), 
            module.isEnabled() ? 0xFFFFFFFF : 0xFFAAAAAA
        );
    }

    @Override
    public int getHeight() {
        return 14; // Macht die Knöpfe etwas höher und cleaner als Standard (Standard ist oft 12)
    }

    private boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
