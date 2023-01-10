#version 330

in vec3 o_texCoord;
out vec4 color;
uniform vec4 c;
uniform sampler2D texSamplers[8];

in vec3 o_color;
void main() {
	if(o_texCoord.z==-1)
	{
		color=vec4(o_color,1);
	}
	else{
	vec2 txc=vec2(o_texCoord.x,o_texCoord.y);
	color=texture(texSamplers[int(o_texCoord.z)],txc);
	}

}
