package mchorse.mclib.utils;

import org.apache.commons.lang3.StringUtils;

public class Color
{
	public float r;
	public float g;
	public float b;
	public float a = 1;

	public Color set(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;

		return this;
	}

	public Color set(float value, int component)
	{
		switch (component)
		{
			case 1:
				this.r = value;
			break;

			case 2:
				this.g = value;
			break;

			case 3:
				this.b = value;
			break;

			default:
				this.a = value;
			break;
		}

		return this;
	}

	public Color set(int color)
	{
		return this.set(color, true);
	}

	public Color set(int color, boolean alpha)
	{
		this.set((color >> 16 & 0xff) / 255F, (color >> 8 & 0xff) / 255F, (color & 0xff)  / 255F, alpha ? (color >> 24 & 0xff)  / 255F : 1F);

		return this;
	}

	public Color copy(Color color)
	{
		this.set(color.r, color.g, color.b, color.a);

		return this;
	}

	public int getRGBAColor()
	{
		return ColorUtils.rgbaToInt(this.r, this.g, this.b, this.a);
	}

	public int getRGBColor()
	{
		return this.getRGBAColor() & 0xffffff;
	}

	public String stringify()
	{
		return this.stringify(false);
	}

	public String stringify(boolean alpha)
	{
		if (alpha)
		{
			return "#" + StringUtils.leftPad(Integer.toHexString(this.getRGBAColor()), 8, '0');
		}

		return "#" + StringUtils.leftPad(Integer.toHexString(this.getRGBColor()), 6, '0');
	}
}