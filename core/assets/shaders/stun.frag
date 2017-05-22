varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;

void main(){
	vec4 color = texture2D(u_texture, v_texCoord0) * v_color;
	//color.xyz = min(min(color.x, color.y), color.z);
	color.xyz = 1 - color.xyz;
	gl_FragColor = color;
}