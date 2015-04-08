package ru.serjik.sprite.animation;

import android.graphics.Bitmap;

public class MotionFrames
{
	public Bitmap[] frames;

	public MotionFrames(Bitmap bitmap)
	{
		int size = bitmap.getHeight();
		int count = bitmap.getWidth() / size;

		frames = new Bitmap[count];

		for (int i = 0; i < count; i++)
		{
			Bitmap frame = Bitmap.createBitmap(bitmap, i * size, 0, size, size);
		}
	}
}
