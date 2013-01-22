package org.rhinobot;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;
import org.rhinobot.ui.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class AndroidScriptProvider implements ModuleScriptProvider {

    private static final String TAG = AndroidScriptProvider.class
            .getSimpleName();

    private AssetManager assets;

    public AndroidScriptProvider(AssetManager assets) {
        this.assets = assets;
    }

    @Override
    public ModuleScript getModuleScript(Context cx, String moduleId,
            URI moduleUri, URI baseUri, Scriptable paths) throws Exception {

        // first try looking in APk assets
        File path = new File("js", moduleId + ".js");
        InputStream assetStream = null;
        try {
            assetStream = assets.open(path.toString());
        } catch (IOException e) {
            Log.e(TAG, "no such module in assets: " + path);
        }

        if (assetStream == null) {
            path = new File(MainActivity.EXT_MODULE_DIR, moduleId + ".js");
            if (path.exists() && path.isFile()) {
                assetStream = new FileInputStream(path);
            } else {
                return null;
            }
        }
        Log.d(TAG, "LOADING URI " + path);
        Reader reader = new InputStreamReader(assetStream);

        final ModuleScript moduleScript = new ModuleScript(
                cx.compileReader(reader, moduleId, 1, null),
                new URI("assets:///" + path), baseUri);

        return moduleScript;
    }

}
