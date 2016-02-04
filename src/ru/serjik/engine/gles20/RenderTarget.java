package com.example.rtt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.opengl.GLES20;

public class RenderTarget
{
	private final int[] genbuf = new int[1];

	private int renderTexure = 0;
	private int frameBuffer = 0;

	private int width, height;

	public RenderTarget(int width, int height, boolean isTexture)
	{
		this.width = width;
		this.height = height;

		if (isTexture)
		{
			GLES20.glGenTextures(1, genbuf, 0);
			renderTexure = genbuf[0];
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renderTexure);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

			IntBuffer texBuffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
			GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, texBuffer);

			GLES20.glGenRenderbuffers(1, genbuf, 0);
			int depthBuf = genbuf[0];
			GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, depthBuf);
			GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height);

			GLES20.glGenFramebuffers(1, genbuf, 0);
			frameBuffer = genbuf[0];
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
			GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, renderTexure, 0);
			GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, depthBuf);

			int res = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);

			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		}
	}

	public void set()
	{
		GLES20.glViewport(0, 0, width, height);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
	}

	public int getRenderTexture()
	{
		return renderTexure;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
