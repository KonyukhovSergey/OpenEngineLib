package ru.serjik.engine.utils;

public class TimeCounter
{
	private static final int MAX_DELAY = 100	;
	private long lastTimeTick = 0;

	public int deltaTimeMillis()
	{
		long currentTimeTick = System.currentTimeMillis();

		int deltaTimeTick = (int) (currentTimeTick - lastTimeTick);

		if (deltaTimeTick > MAX_DELAY)
		{
			deltaTimeTick = MAX_DELAY;
		}

		lastTimeTick = currentTimeTick;

		return deltaTimeTick;
	}

	public float deltaTimeSeconds()
	{
		return deltaTimeMillis() * 0.001f;
	}
}
