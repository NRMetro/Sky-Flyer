package io.github.skyflyer;

import com.badlogic.gdx.ApplicationAdapter;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    @Override
    public void create() {
        new SkyFly();

    }
}
