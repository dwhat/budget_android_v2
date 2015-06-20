import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import de.budget.BudgetAndroid.LoginActivity;
import de.budget.R;

/**
 * Created by mark on 20/06/15.
 */
public class MyFirstTestActivityTest
        extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginTestActivity;
    private EditText mFirstTestText;

    public MyFirstTestActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        loginTestActivity = getActivity();
        mFirstTestText =
                (EditText) loginTestActivity
                        .findViewById(R.id.username);
    }

    public void testPreconditions() {
        assertNotNull("loginTestActivity is null", loginTestActivity);
        assertNotNull("mFirstTestText is null", mFirstTestText);
    }

    public void testMyFirstTestTextView_labelText() {
        final String expected =
                loginTestActivity.getString(R.string.username);
        final String actual = mFirstTestText.getText().toString();
        assertEquals(expected, actual);
    }
}