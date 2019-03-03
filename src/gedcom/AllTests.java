package gedcom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CheckValidityTest.class, TestDateCheck.class, TestUS05MarriageBeforeDeath.class,
		TestUS08BirthBeforeMarriageOfParents.class, TestUS02_US03Test.class })
public class AllTests {

}
