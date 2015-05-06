package ru.serjik.math;

public class Matrix2D
{
	public final static void multiply(float[] a, float b[], float c[])
	{
		c[0] = a[0] * b[0] + a[1] * b[3];
		c[1] = a[0] * b[1] + a[1] * b[4];
		c[2] = a[0] * b[2] + a[1] * b[5] + a[2];

		c[3] = a[3] * b[0] + a[4] * b[3];
		c[4] = a[3] * b[1] + a[4] * b[4];
		c[5] = a[3] * b[2] + a[4] * b[5] + a[5];
	}

	public final static void identity(final float m[])
	{
		m[0] = m[4] = 1;
		m[1] = m[2] = m[3] = m[5] = 0;
	}

	public final static void translate(final float m[], float dx, float dy)
	{
		m[0] = m[4] = 1;
		m[1] = m[3] = 0;
		m[2] = dx;
		m[5] = dy;
	}

	public final static void scale(final float m[], float sx, float sy)
	{
		m[0] = sx;
		m[4] = sy;
		m[1] = m[2] = m[3] = m[5] = 0;
	}

	public final static void rotate(final float m[], float angle)
	{
		m[2] = m[5] = 0;
		m[4] = m[0] = (float) Math.cos(angle);
		m[3] = (float) Math.sin(angle);
		m[1] = -m[3];
	}
}
