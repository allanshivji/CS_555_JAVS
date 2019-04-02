package gedcom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CheckValidityTest.class, Test_DateCheck.class, Test_US05MarriageBeforeDeath.class,
		Test_US08BirthBeforeMarriageOfParents.class, Test_US02_US03.class, Test_US21CheckGenderRole.class, Test_US22UniqueIds.class,
		Test_US05MarriageBeforeDeath.class,Test_US15FewerThanFifteenSiblings.class,Test_US04MarriageAfterDivorce.class,
		Test_US24UniqueFamilyBySpouse.class})
public class AllTests {

}
