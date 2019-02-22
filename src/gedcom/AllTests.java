package gedcom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CheckValidityTest.class, DateCheck.class, TestUS05MarriageBeforeDeath.class,
		TestUS08BirthBeforeMarriageOfParents.class })
public class AllTests {

}
