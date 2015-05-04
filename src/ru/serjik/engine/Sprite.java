package ru.serjik.engine;

public class Sprite
{
	private final float[] v = new float[30];
	private Tile tile;

	public Sprite(Tile tile)
	{
		tile(tile);
		color(ColorTools.WHITE_XFFFF);
		position(0, 0);
	}

	public Sprite()
	{
		color(ColorTools.WHITE_XFFFF);
	}

	public Sprite tile(Tile tile)
	{
		if (this.tile != tile)
		{
			this.tile = tile;
			v[2] = v[17] = v[27] = tile.u1;
			v[3] = v[8] = v[18] = tile.v1;
			v[7] = v[12] = v[22] = tile.u2;
			v[13] = v[23] = v[28] = tile.v2;
		}
		return this;
	}

	public void draw(BatchDrawer bd)
	{
		bd.draw(v, BatchDrawer.TEXTURED | BatchDrawer.COLORED);
	}

	public Sprite color(final float color)
	{
		v[4] = v[9] = v[14] = v[19] = v[24] = v[29] = color;

		return this;
	}

	public Sprite position(final float[] m)
	{
		v[15] = v[0] = m[0] * tile.x1 + m[1] * tile.y1 + m[2];
		v[16] = v[1] = m[3] * tile.x1 + m[4] * tile.y1 + m[5];
		v[5] = m[0] * tile.x2 + m[1] * tile.y1 + m[2];
		v[6] = m[3] * tile.x2 + m[4] * tile.y1 + m[5];
		v[10] = v[20] = m[0] * tile.x2 + m[1] * tile.y2 + m[2];
		v[11] = v[21] = m[3] * tile.x2 + m[4] * tile.y2 + m[5];
		v[25] = m[0] * tile.x1 + m[1] * tile.y2 + m[2];
		v[26] = m[3] * tile.x1 + m[4] * tile.y2 + m[5];

		return this;
	}

	public Sprite position(float px, float py)
	{
		v[0] = v[15] = v[25] = tile.x1 + px;
		v[1] = v[6] = v[16] = tile.y1 + py;
		v[5] = v[10] = v[20] = tile.x2 + px;
		v[11] = v[21] = v[26] = tile.y2 + py;

		return this;
	}

	public Sprite position(float px, float py, float sx, float sy)
	{
		v[0] = v[15] = v[25] = tile.x1 * sx + px;
		v[1] = v[6] = v[16] = tile.y1 * sy + py;
		v[5] = v[10] = v[20] = tile.x2 * sx + px;
		v[11] = v[21] = v[26] = tile.y2 * sy + py;

		return this;
	}

	public float[] data()
	{
		return v;
	}
}
