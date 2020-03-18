package mchorse.mclib.client.gui.framework.elements;

import java.util.ArrayList;
import java.util.List;

import mchorse.mclib.utils.Direction;
import mchorse.mclib.client.gui.utils.Area;
import mchorse.mclib.client.gui.framework.elements.utils.GuiDrawable;
import mchorse.mclib.client.gui.utils.Icon;
import mchorse.mclib.client.gui.utils.resizers.Resizer.Measure;
import mchorse.mclib.client.gui.widgets.buttons.GuiTextureButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * Panel base GUI
 * 
 * With this base class, you can add multi panel elements which could be 
 * switched between using buttons.
 */
public class GuiPanelBase<T extends IGuiElement> extends GuiElement
{
    public GuiDelegateElement<T> view;
    public GuiElements<GuiButtonElement<GuiTextureButton>> buttons;
    public List<T> panels = new ArrayList<T>();
    public Direction direction;

    public GuiPanelBase(Minecraft mc)
    {
        this(mc, Direction.BOTTOM);
    }

    public GuiPanelBase(Minecraft mc, Direction direction)
    {
        super(mc);

        this.direction = direction == null ? Direction.BOTTOM : direction;
        this.view = new GuiDelegateElement<T>(mc, null);
        this.view.resizer().parent(this.area).set(0, 0, 1, 1, Measure.RELATIVE).h(1, -20);

        if (this.direction == Direction.TOP)
        {
            this.view.resizer().y(20);
        }
        else if (this.direction == Direction.LEFT)
        {
            this.view.resizer().x(20).w(1, -20).h(1, 0);
        }
        else if (this.direction == Direction.RIGHT)
        {
            this.view.resizer().w(1, -20).h(1, 0);
        }

        this.buttons = new GuiElements<GuiButtonElement<GuiTextureButton>>();
        GuiDrawable drawable = new GuiDrawable((v) ->
        {
            for (int i = 0, c = this.panels.size(); i < c; i++)
            {
                if (this.view.delegate == this.panels.get(i))
                {
                    Area area = this.buttons.elements.get(i).area;

                    Gui.drawRect(area.x - 2, area.y - 2, area.getX(1) + 2, area.getY(1) + 2, 0x880088ff);
                }
            }
        });

        this.add(drawable, this.buttons, this.view);
    }

    /**
     * Register a panel with given texture and tooltip 
     */
    public GuiButtonElement<GuiTextureButton> registerPanel(T panel, String tooltip, Icon icon)
    {
        GuiButtonElement<GuiTextureButton> button = GuiButtonElement.icon(this.mc, icon, (b) -> this.setPanel(panel));

        if (tooltip != null && !tooltip.isEmpty())
        {
            button.tooltip(tooltip, this.direction.opposite());
        }

        this.setupButtonResizer(button);
        this.panels.add(panel);
        this.buttons.add(button);

        return button;
    }

    /**
     * Here subclasses can override the logic for how the buttons should 
     * be setup 
     */
    protected void setupButtonResizer(GuiButtonElement<GuiTextureButton> button)
    {
        if (this.buttons.elements.isEmpty())
        {
            if (this.direction.isHorizontal())
            {
                button.resizer().parent(this.area).set(2, 2, 16, 16);

                if (this.direction == Direction.RIGHT)
                {
                    button.resizer().x(1, -18);
                }
            }
            else
            {
                boolean bottom = this.direction == Direction.BOTTOM;

                button.resizer().parent(this.area).set(0, 0, 16, 16).x(1, -18).y(bottom ? 1 : 0, bottom ? -18 : 2);
            }
        }
        else
        {
            GuiButtonElement<GuiTextureButton> last = this.buttons.elements.get(this.buttons.elements.size() - 1);

            int x = this.direction.isHorizontal() ? -20 : 0;
            int y = this.direction.isVertical() ? -20 : 0;

            button.resizer().relative(last.resizer()).set(x, y, 16, 16);
        }
    }

    /**
     * Switch current panel to given one
     */
    public void setPanel(T panel)
    {
        this.view.setDelegate(panel);
    }

    @Override
    public void draw(GuiContext context)
    {
        if (this.direction == Direction.TOP)
        {
            this.drawBackground(context, this.area.x, this.area.y, this.area.w, 20);
        }
        else if (this.direction == Direction.BOTTOM)
        {
            this.drawBackground(context, this.area.x, this.area.getY(1) - 20, this.area.w, 20);
        }
        else if (this.direction == Direction.LEFT)
        {
            this.drawBackground(context, this.area.x, this.area.y, 20, this.area.h);
        }
        else
        {
            this.drawBackground(context, this.area.getX(1) - 20, this.area.y, 20, this.area.h);
        }

        super.draw(context);
    }

    protected void drawBackground(GuiContext context, int x, int y, int w, int h)
    {}
}