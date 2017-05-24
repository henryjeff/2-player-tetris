extern float	time; 
extern vec2     imageSize;

#ifdef PIXEL
vec4 effect(vec4 vColor, sampler2D texture, vec2 uv, vec2 vScreen )
{
    float frequency = 8.0;
    float phase = time * 0.1;
    float magnitude = 2.0; //This is in pixels since we're scaling by 1/resolution

    vec4 color = texture2D(texture, uv + vec2( sin(phase + frequency * uv.y) * magnitude, 0 ) * imageSize );

    return color;
}
#endif