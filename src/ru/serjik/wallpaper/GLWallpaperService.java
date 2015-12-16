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

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			if (visible)
			{
				renderRequester.resume(view);
			}
			else
			{
				renderRequester.pause();
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder)
		{
			// Log.v("glws", "onSurfaceCreated");
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
			// Log.v("glws", "onSurfaceDestroyed");
			view.surfaceDestroyed(holder);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep,
				int xPixelOffset, int yPixelOffset)
		{
			wallpaperOffsetsListener.onOffsetChanged(xOffset - 0.5f, yOffset);
		};
	}

	public abstract WallpaperOffsetsListener onRendererAcquire(GLSurfaceView view);
}
