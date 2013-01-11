var {Toast} = android.widget;
var {Log} = android.util;

// display a message
exports.alert = function(message) {
    Log.d("JS Alert", message);
    Toast.makeText(activity, String(message), Toast.LENGTH_SHORT).show();
}
