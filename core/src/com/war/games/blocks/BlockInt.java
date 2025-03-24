package com.war.games.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BlockInt extends Block {
    private static final Texture visuel = new Texture(Gdx.files.internal("blocks/cave.png"));


    public BlockInt(int x, int y){
        super(x, y, BlockInt.visuel);
    }
}
