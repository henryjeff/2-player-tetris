extern float	time; 
extern vec2		imageSize; 
extern vec3     outlineColor;


#ifdef PIXEL
vec4 effect(vec4 vColor, sampler2D texture, vec2 uv, vec2 vScreen )
{
    vec4 outline;
    outline.xyz = outlineColor;
    //sample 4 times, for each offset.
    outline.w = texture2D( texture, uv + vec2(imageSize.x,0) ).w;
    outline.w += texture2D( texture, uv + vec2(-imageSize.x,0) ).w;
    outline.w += texture2D( texture, uv + vec2(0,imageSize.y) ).w;
    outline.w += texture2D( texture, uv + vec2(0,-imageSize.y) ).w;

    // Uncomment these for full corner outlines!
    // outline.w += texture2D( texture, uv + vec2(imageSize.x,imageSize.y) ).w;
    // outline.w += texture2D( texture, uv + vec2(-imageSize.x,-imageSize.y) ).w;
    // outline.w += texture2D( texture, uv + vec2(-imageSize.x,imageSize.y) ).w;
    // outline.w += texture2D( texture, uv + vec2(imageSize.x,-imageSize.y) ).w;

    vec4 color = texture2D(texture, uv );

    color.xyz = mix(outline.xyz,color.xyz, color.w);
    color.w = outline.w;

    return color;
}
#endif