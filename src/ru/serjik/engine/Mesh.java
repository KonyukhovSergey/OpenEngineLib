package ru.serjik.engine;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Mesh
{
	private FloatBuffer data;

	public Mesh(FloatBuffer data)
	{
		this.data = data;
	}

	public void bind(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		data.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, (3 + 3 + 2) * 4, data);

		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		data.position(3);
		gl.glNormalPointer(GL10.GL_FLOAT, (3 + 3 + 2) * 4, data);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		data.position(6);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, (3 + 3 + 2) * 4, data);
	}

	public void draw(GL10 gl)
	{
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, data.capacity() / (3 + 3 + 2));
	}
}
