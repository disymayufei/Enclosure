package com.github.zly2006.enclosure.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ConfirmScreen extends Screen {
    private static final Identifier TEXTURE = new Identifier("textures/gui/demo_background.png");
    Screen parent;
    Text message;
    Runnable action;
    ButtonWidget yesButton;
    ButtonWidget noButton;

    public ConfirmScreen(Screen parent, Text message, Runnable action) {
        super(Text.of("Confirm"));
        this.parent = parent;
        this.message = message;
        this.action = action;
    }

    @Override
    protected void init(){
        super.init();

        yesButton = new ButtonWidget((parent.width / 2 - 95), 0, 90, 20, Text.translatable("enclosure.widget.yes"), button -> {
            action.run();
            assert client != null;
            client.setScreen(parent);
        });

        noButton = new ButtonWidget(parent.width / 2 + 5, 0, 90, 20, Text.translatable("enclosure.widget.no"), button -> {
            assert client != null;
            client.setScreen(parent);
        });

        addSelectableChild(yesButton);
        addSelectableChild(noButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        parent.render(matrices, 0, 0, delta);
        renderBackground(matrices);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int height = 150;
        int x = (parent.width - 200) / 2;
        int y = (parent.height - height) / 2;
        int linesY = y + 10;
        drawTexture(matrices, x, y, 0, 0, 200, 150, 200, 150);
        List<OrderedText> lines = textRenderer.wrapLines(message, 180);
        for (OrderedText line : lines) {
            textRenderer.draw(matrices, line, x + 10, linesY, 0xFFFFFF);
            linesY += 10;
        }
        yesButton.y = (y + height - 30);
        noButton.y = (y + height - 30);
        yesButton.render(matrices, mouseX, mouseY, delta);
        noButton.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
