#version 330


layout(location=0) in vec3 position;
layout(location=1) in vec3 color;
layout(location=2) in vec3 texCoord;

out vec3 o_texCoord;
out vec3 o_color;
uniform mat4 uProjection;
uniform mat4 uView;


void main() {
	o_texCoord=texCoord;
	o_color=color;
    gl_Position=uProjection*uView*vec4(position, 1.0);
}