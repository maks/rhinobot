package org.rhinobot;

import android.content.res.AssetManager;
import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class AndroidScriptProvider implements ModuleScriptProvider {

    private static final String TAG = null;
    private AssetManager assets;

    public AndroidScriptProvider(AssetManager assets) {
        this.assets = assets;
    }

    @Override
    public ModuleScript getModuleScript(Context cx, String moduleId,
            URI moduleUri, URI baseUri, Scriptable paths) throws Exception {

        String path = "js/" + moduleId + ".js";

        Log.d(TAG, "LOADING URI " + path);

        Reader reader = new InputStreamReader(assets.open(path));

        final ModuleScript moduleScript = new ModuleScript(
                cx.compileReader(reader, moduleId, 1, null),
                new URI("assets:///" + path), baseUri);

        return moduleScript;
    }

}
