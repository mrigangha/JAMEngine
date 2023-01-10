package JAM;

public class DefaultShader extends Shader{

	
	private static final String VS_SRC="res/shaders/Default.vs.glsl";
	private static final String FS_SRC="res/shaders/Default.fs.glsl";
	public DefaultShader() {
		super(VS_SRC, FS_SRC);
	}

}
