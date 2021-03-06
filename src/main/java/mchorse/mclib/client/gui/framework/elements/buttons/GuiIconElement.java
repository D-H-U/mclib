package mchorse.mclib.client.gui.framework.elements.buttons;

import mchorse.mclib.client.gui.framework.elements.utils.GuiContext;
import mchorse.mclib.client.gui.framework.elements.utils.GuiDraw;
import mchorse.mclib.client.gui.utils.Icon;
import mchorse.mclib.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.util.function.Consumer;

public class GuiIconElement extends GuiClickElement<GuiIconElement>
{
	public Icon icon;
	public Icon iconHover;
	public int hoverColor = 0xffaaaaaa;

	public GuiIconElement(Minecraft mc, Icon icon, Consumer<GuiIconElement> callback)
	{
		super(mc, callback);

		this.icon = icon;
		this.iconHover = icon;
	}

	public GuiIconElement both(Icon icon)
	{
		this.icon = this.iconHover = icon;

		return this;
	}

	public GuiIconElement icon(Icon icon)
	{
		this.icon = icon;

		return this;
	}

	public GuiIconElement hovered(Icon icon)
	{
		this.iconHover = icon;

		return this;
	}

	public GuiIconElement hoverColor(int color)
	{
		this.hoverColor = color;

		return this;
	}

	@Override
	protected void drawSkin(GuiContext context)
	{
		Icon icon = this.hover ? this.iconHover : this.icon;
		int color = this.hover ? this.hoverColor : 0xffffffff;

		if (this.isEnabled())
		{
			ColorUtils.bindColor(color);
			icon.render(this.area.mx(), this.area.my(), 0.5F, 0.5F);
		}
		else
		{
			GuiDraw.drawOutlinedIcon(icon, color, this.area.mx(), this.area.my(), 0.5F, 0.5F);

			GlStateManager.color(0, 0, 0, 0.5F);
			icon.render(this.area.mx(), this.area.my(), 0.5F, 0.5F);
		}
	}
}