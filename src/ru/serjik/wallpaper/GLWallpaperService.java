package ru.serjik.wallpaper;

import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import ru.serjik.engine.utils.RenderRequester;

public abstract class GLWallpaperService extends WallpaperService
{
	@Override
	public Engine onCreateEngine()
	{
		return new GLWallpaperEngine();
	}

	public class GLWallpaperEngine extends WallpaperService.Engine
	{
		private GLSurfaceView view = null;
		private RenderRequester renderRequester = new RenderRequester();
		private WallpaperOffsetsListener wallpaperOffsetsListener;

		boolean isOnOffsetsChangedWorking = false;
		private float lastOffsetValue = 0;
		private int offsetChangesCount = 0;

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			// Log.v("glwps", "visible = " + visible + " view =" + view);
			if (visible)
			{
				initOffsetWorkingDetector();
				view.onResume();
				renderRequester.resume(view);
			}
			else
			{
				renderRequester.pause();
				view.onPause();
			}
		}

		private void initOffsetWorkingDetector()
		{
			isOnOffsetsChangedWorking = false;
			lastOffsetValue = 0;
			offsetChangesCount = 0;
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder)
		{
			view = new GLSurfaceView(GLWallpaperService.this)
			{
				@Override
				public SurfaceHolder getHolder()
				{
					return GLWallpaperEngine.this.getSurfaceHolder();
				}
			};
			wallpaperOffsetsListener = onRendererAcquire(view);
			view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder)
		{
			view.surfaceDestroyed(holder);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep,
				int xPixelOffset, int yPixelOffset)
		{
			if (isOnOffsetsChangedWorking)
			{
				if (xOffset < 0.0f || xOffset > 1.0f)
				{
					wallpaperOffsetsListener.onOffsetChanged(0, 0);
				}
				else
				{
					wallpaperOffsetsListener.onOffsetChanged(xOffset - 0.5f, 0);
				}
			}
			else
			{
				if (offsetChangesCount > 3)
				{
					isOnOffsetsChangedWorking = true;
					wallpaperOffsetsListener.onOffsetChanged(xOffset - 0.5f, 0);
				}
				else
				{
					if (Math.abs(xOffset - lastOffsetValue) > 0.001f)
					{
						offsetChangesCount++;
						lastOffsetValue = xOffset;
					}
					else
					{
						offsetChangesCount = 0;
					}
				}
			}
		};

	}

	public abstract WallpaperOffsetsListener onRendererAcquire(GLSurfaceView view);
}
