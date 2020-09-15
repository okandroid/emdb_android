package com.epigra.emdb.core

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.FragmentManager
import com.epigra.emdb.utils.T
import com.epigra.emdb.utils.managers.AppDataCacheManager
import com.epigra.emdb.utils.managers.AppPreferencesManager
import org.jetbrains.anko.AnkoLogger


abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    val mActivity: BaseActivity by lazy { this }
    val mApplication: AppEmdb by lazy { application as AppEmdb }

    val mDataCache: AppDataCacheManager by lazy { mApplication.getAppDataCache() }
    val mPrefsManager: AppPreferencesManager by lazy { mApplication.getSharedPreferencesManager() }

    /**
     * Required when a fragment needs to be inflated within activity context.
     */
    val mFragmentManager: FragmentManager by lazy { supportFragmentManager }

    var toolbar: Toolbar? = null
    var mArguments: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set itemData view with the given resource id.
        setContentView(getContentLayoutID())

        // Reach to the layout objects.
        findUI()
        setupUI(savedInstanceState)

        // Fetch bundle arguments.
        mArguments = intent.extras
        mArguments?.let { mArguments = it.getBundle(T.ACTIVITY_BUNDLE_EXTRA) }
    }

    /**
     * Sets the layout id to current view and enables basic swipe to refresh feature.
     *
     * @param layoutResID is the layout id.
     */
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    /**
     * Before destroying the current activity, unregister the bus to release attached callbacks.
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * Called as the next activity has started with
     * [.startAppActivityForResult]. As the next
     * activity returns any data, this redirects the itemData to
     * [.onActivityResultFetched] to be gathered in parent activities. All
     * the activities that extends BaseActivity class will implement
     * [.onActivityResultFetched] method.
     *
     * @param requestCode is the code to start next activity.
     * @param resultCode  is the result code to show whether the status of fetched data is OK or NOT OK.
     * @param data        is the itemData bundle that holds data.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultFetched(requestCode, resultCode, data)
    }

    /**
     * Called for the result of as the user is prompted to give any permission to do desired tasks.
     * Generally runtime permissions are fired and result is fetched by this method. After api-23
     * Marshmallow, runtime permissions are introduced and those permissions have their own built in
     * dialogs to be shown to user. Only so-called 'Dangerous Permissions' are required to show
     * prompt dialog. Pre-Marshmallow devices fetches those permissions at the install time.
     *
     * @param requestCode  is the 8-bit code to fetch permission result. (2^3)
     * @param permissions  an array to declare which permissions are wanted.
     * @param grantResults is the result array that shows the result of wanted permissions.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var grantedPermissions = arrayOf<String>()
        var deniedPermissions = arrayOf<String>()
        permissions.indices.forEach { i ->
            when (grantResults[i]) {
                PackageManager.PERMISSION_GRANTED -> grantedPermissions =
                    grantedPermissions.plus(permissions[i])
                else -> deniedPermissions = deniedPermissions.plus(permissions[i])
            }
        }

        // Return the granted permissions set.
        onPermissionResultFetched(requestCode, grantedPermissions, deniedPermissions)
    }

    @Nullable
    override fun getSupportParentActivityIntent(): Intent? {
        val intent = super.getSupportParentActivityIntent()
        intent?.let { it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        return intent
    }

    /**
     * Returns the string that will be sent to Google Analytics Service.
     *
     * @return a string to send as an analytics data to be sent to Google Analytics service.
     */
    abstract fun getAnalyticsScreenName(): String?

    /**
     * Returns the layout id of current activity itemData view.
     *
     * @return the id of layout resource file.
     */
    protected abstract fun getContentLayoutID(): Int

    /**
     * Find the current layout's UI references.
     */
    protected abstract fun findUI()

    /**
     * Initialization method to complete tasks.(Listeners, setters etc.)
     *
     * @param savedInstanceState is the bundle that holds the previous state of activity.
     */
    protected abstract fun setupUI(savedInstanceState: Bundle?)

    /**
     * Called every time as the the target activity is started with
     * [.startAppActivityForResult] This returns any data that's
     * been fetched from the target activity as a result.
     *
     * @param requestCode code for the data retrieval.
     * @param resultCode  result code for data that's been fetched. Usually OK or NOT OK.
     * @param data        is the bundle for the data that's been fetched.
     */
    abstract fun onActivityResultFetched(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * A custom implementation for the [.onRequestPermissionsResult]
     * Called as the permission prompt(s) dialogs are shown and results are needed to be processed.
     * While coding, if you get the Boolean.TRUE values, you will get a collection that contains
     * which permissions are granted. Boolean.FALSE collection contains the permissions that are
     * denied. To be able to continue to the rest of your code, check the size/length of your
     * collection which you send to [.requestRuntimePermissions] to make sure if
     * matches or not. For the denied ones, you can show
     * {link ActivityCompat_shouldShowRequestPermissionRationale(Activity, String)} to clearly explain
     * why you need that permission.
     *
     * @param requestCode
     * @param grantedPermissions holds granted permissions as a set. Check if your permissions
     * do exist in the returning set by calling {link Set#containsAll(Collection)}
     * for those which are not granted, show rationale.
     * [android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(Activity, String)]
     * If permission denied previously, this time there will be a
     * "Never ask again" checkbox in the permission dialog.
     * Call [shouldShowPermissionRationale] to see if user
     * selected "Never ask again". [.shouldShowPermissionRationale]
     * method returns false only if user selected "Never ask again"
     * or device policy prohibits the app from having that permission.
     * @param deniedPermissions   holds denied permissions. If it's totally empty, than all the permissions
     * are given.
     * see ActivityCompat#shouldShowRequestPermissionRationale(Activity, String)
     */
    protected abstract fun onPermissionResultFetched(
        requestCode: Int,
        grantedPermissions: Array<String>,
        deniedPermissions: Array<String>
    )

    /**
     * An implementation for the Sevenful Event Bus to retrieve passed data. It has been written here
     * intentionally to not to forget bus interface.
     *
     * @param data the itemData of data that's been passed via
     * event bus. Don't forget to add @Subscriber
     * annotation for this method. It is always better
     * to implement with tag, and [org.simple.eventbus.ThreadMode]
     * to let know all listeners about the action
     * they'll take as the data is fetched. Note that
     * data can be any class so it would be better to
     * evaluate it via #instanceOf equation.
     *
     *
     * Example usage: @Subscriber(mode = ThreadMode.MAIN, tag = T.busTags.PROFILE_TAG)
     * @param <E>  is the generic class of passed data. It can be
     * casted to any class. </E>
     */
    protected abstract fun <E> onBusDataFetched(data: E)

    /**
     * Post your data to evoke all-related listeners (indicated
     * with tag) which are attached on the bus.
     *
     * @param data is the itemData of data that'll passed via event bus.
     * @param tag  tag for the listeners. It indicates which listeners will be evoked as the data is passed.
     * @param <E>  itemType of data. As it's a generic itemType, it can be anything as casted. </E>
     */
    fun <E> postBusData(data: E, tag: String) {
        //mEventBus.post(data, tag)
    }


    /**
     * Call to enable back arrow for the activities.
     */
    protected fun setDisplayHomeOptions() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)

            // Check if the activity has label.
            val title = packageManager
                .getActivityInfo(componentName, PackageManager.GET_META_DATA)
                .loadLabel(packageManager)

            // Set activity's title.
            setActivityTitle(title)
        }
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * Sets the title of current activity's toolbar text.
     *
     * @param stringRes is the text for setting toolbar's title.
     */
    protected fun setActivityTitle(@StringRes stringRes: Int) {
        toolbar?.setTitle(stringRes)
    }

    /**
     * Sets the title of current activity's toolbar text.
     *
     * @param title is the string to set toolbar title.
     */
    protected fun setActivityTitle(title: CharSequence) {
        toolbar?.title = title
    }

    /**
     * Sets the title of current activity's toolbar subtitle text.
     *
     * @param stringRes is the text for setting toolbar's subtitle.
     */
    protected fun setActivitySubTitle(@StringRes stringRes: Int) {
        toolbar?.setSubtitle(stringRes)
    }

    /**
     * Sets the title of current activity's toolbar subtitle text.
     *
     * @param subtitle is the string to set toolbar subtitle.
     */
    protected fun setActivitySubTitle(subtitle: CharSequence) {
        toolbar?.subtitle = subtitle
    }

    /**
     * Requesting runtime permissions for the devices Marshmallow
     * or above Marshmallow. These devices usually show a prompt
     * dialog that asks user to give appropriate permission for
     * 'Dangerous Permissions'. Pre-Marshmallow devices will have
     * the permissions at install time.
     *
     * @param permissions array of permissions that will be prompted
     * to user.
     */
    fun requestRuntimePermissions(permissionCode: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    /**
     * Sometimes user can be prompted for a single permission. Just
     * like [.requestRuntimePermissions] a permission
     * dialog will be shown. You can check if a permission has been
     * granted already.
     *
     * @param permission is the requested 'Dangerous Permission' for
     * Marshmallow.
     * @return if the requested permission has been already granted.
     */
    fun checkSelfRuntimePermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Shows a rationale for the given permission. Rational is only shown
     * if the permission is denied.
     *
     * @param permission the required permission that needs to show
     * rationale.
     */
    fun showPermissionRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }

    /**
     * Shows a rationale for the given permission. Rational is only shown
     * if the permission is denied.
     *
     * @param permission the required permission that needs to show
     * rationale.
     */
    fun shouldShowPermissionRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }
}