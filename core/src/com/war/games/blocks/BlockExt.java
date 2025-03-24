package com.war.games.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.war.games.screens.Shooter;

public class BlockExt extends Block {
    private static final String pathImgUpsideDown = "blocks/UpsideDown/";
    private static final String pathImgPlainStitch = "blocks/PlainStitch/";


    private static final Texture  diams = new Texture(Gdx.files.internal(BlockExt.pathImgPlainStitch + "diams.png"));
    private static final Texture  UpsideDowndiams = new Texture(Gdx.files.internal(BlockExt.pathImgUpsideDown + "diams.png"));


    private static final Texture  SlopeTopRight = new Texture(Gdx.files.internal(BlockExt.pathImgPlainStitch + "diamsSlopeTopRight.png"));
    private static final Texture  SlopeTopLeft = new Texture(Gdx.files.internal(BlockExt.pathImgPlainStitch + "diamsSlopeTopLeft.png"));

    private static final Texture  UpsideDownSlopeTopRight = new Texture(Gdx.files.internal(BlockExt.pathImgUpsideDown + "diamsSlopeTopRight.png"));
    private static final Texture  UpsideDownSlopeTopLeft = new Texture(Gdx.files.internal(BlockExt.pathImgUpsideDown + "diamsSlopeTopLeft.png"));

    private static final Texture  SlopeBottomRight = new Texture(Gdx.files.internal(BlockExt.pathImgPlainStitch + "diamsSlopeBottomRight.png"));
    private static final Texture  SlopeBottomLeft = new Texture(Gdx.files.internal(BlockExt.pathImgPlainStitch + "diamsSlopeBottomLeft.png"));

    private static final Texture  UpsideDownSlopeBottomRight = new Texture(Gdx.files.internal(BlockExt.pathImgUpsideDown + "diamsSlopeBottomRight.png"));
    private static final Texture  UpsideDownSlopeBottomLeft = new Texture(Gdx.files.internal(BlockExt.pathImgUpsideDown + "diamsSlopeBottomLeft.png"));


    public BlockExt(int x, int y, char ch){
        super(x, y, BlockExt.getTexture(ch));
    }

    public static Texture getTexture(char ch){
        if (ch == '╩') {
            return BlockExt.diams;
        } if (ch == '╦') {
            return BlockExt.UpsideDowndiams;
        } if (ch == '◣'){
            return BlockExt.SlopeTopRight;
        } if (ch == '◢'){
            return BlockExt.SlopeTopLeft;
        } if (ch == '◤'){
            return BlockExt.UpsideDownSlopeTopRight;
        } if (ch == '◥'){
            return BlockExt.UpsideDownSlopeTopLeft;
        } if (ch == '╔'){
            return BlockExt.SlopeBottomRight;
        } if (ch == '╗'){
            return BlockExt.SlopeBottomLeft;
        } if (ch == '╚'){
            return BlockExt.UpsideDownSlopeBottomRight;
        } if (ch == '╝'){
            return BlockExt.UpsideDownSlopeBottomLeft;
        }
        return null;
    }

}
