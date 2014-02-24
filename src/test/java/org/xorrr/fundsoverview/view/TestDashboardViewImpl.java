package org.xorrr.fundsoverview.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.layouts.AddFundLayout;
import org.xorrr.fundsoverview.layouts.AllLayouts;
import org.xorrr.fundsoverview.layouts.LoginLayout;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

@RunWith(MockitoJUnitRunner.class)
public class TestDashboardViewImpl {

    private DashboardViewImpl view;

    @Mock
    private DashboardViewHandler handler;
    @Mock
    private LoginLayout loginLayout;
    @Mock
    private AddFundLayout addFundLayout;
    @Mock
    private Localization translation;

    private final String EXPECTED_ISIN = "12345";
    private final String EXPECTED_NAME = "foo";
    private final String EXPECTED_PRICE = "23";
    private final String EXPECTED_CURRENT_GROWTH = "25%";
    private final String EXPECTED_ONE_YEAR_GROWTH = "-50%";
    private final String EXPECTED_THREE_YEAR_GROWTH = "100%";
    private final String EXPECTED_FIVE_YEAR_GROWTH = "-125%";

    private Fund f;
    private List<Fund> funds = new ArrayList<>();
    private Item testItem;

    

    private void createTestFundProduct() {
        Fund f = new Fund.Builder().isin(EXPECTED_ISIN).build();
        f.setName(EXPECTED_NAME);
        f.setCurrentPrice(EXPECTED_PRICE);
        f.setCurrentGrowth(EXPECTED_CURRENT_GROWTH);
        f.setOneYearGrowth(EXPECTED_ONE_YEAR_GROWTH);
        f.setThreeYearGrowth(EXPECTED_THREE_YEAR_GROWTH);
        f.setFiveYearGrowth(EXPECTED_FIVE_YEAR_GROWTH);

        this.f = f;
    }

    private void setLocalizationMessages() {
        translation = new Localization();
    }

    private void checkType(Object expected, String id) {
        assertEquals(expected,
                testItem.getItemProperty(translation.getTranslationFor(id))
                        .getType());
    }

    private Item setTestItem() {
        return testItem = view.getFundTable().getItem(0);
    }

    private void checkLabelContent(String expected, String contentVariable) {
        assertEquals(
                expected,
                testItem.getItemProperty(
                        translation.getTranslationFor(contentVariable))
                        .getValue().toString());
    }

    private Label getLabelFor(String l18nvar) {
        return (Label) testItem.getItemProperty(
                translation.getTranslationFor(l18nvar)).getValue();
    }

    private void checkLabelContents() {
        checkLabelContent(EXPECTED_NAME, TranslationVars.FUND);
        checkLabelContent(EXPECTED_PRICE, TranslationVars.PRICE);
        checkLabelContent(EXPECTED_CURRENT_GROWTH,
                TranslationVars.CURRENT_YEAR);
        checkLabelContent(EXPECTED_ONE_YEAR_GROWTH,
                TranslationVars.ONE_YEAR);
        checkLabelContent(EXPECTED_THREE_YEAR_GROWTH,
                TranslationVars.THREE_YEARS);
        checkLabelContent(EXPECTED_FIVE_YEAR_GROWTH,
                TranslationVars.FIVE_YEARS);
    }

    private void checkLabelTypes() {
        checkType(Label.class, TranslationVars.FIVE_YEARS);
        checkType(Label.class, TranslationVars.CURRENT_YEAR);
        checkType(Label.class, TranslationVars.ONE_YEAR);
        checkType(Label.class, TranslationVars.THREE_YEARS);
        checkType(Label.class, TranslationVars.PRICE);
    }

    private void clickDeleteFundButton() {
        view.getDeleteFundButtons().get(0).click();
    }

    private void displayFundsWithDeleteButtonAndTestItem() {
        view.displayFundsWithDeleteButtons(funds);
        setTestItem();
    }

    @Before
    public void setUp() {
        view = new DashboardViewImpl(loginLayout, addFundLayout, translation);
        view.setHandler(handler);
        view.init();

        createTestFundProduct();
        funds.add(f);

        setLocalizationMessages();
    }

    @Test
    public void basicFinancialProductsAreShown() {
        view.displayFunds(funds);

        setTestItem();

        checkLabelContents();
        checkLabelTypes();
        assertNull(testItem.getItemProperty(
                translation.getTranslationFor(TranslationVars.DELETE))
                .toString());
    }

    @Test
    public void assignCorrectStyle() {
        view.displayFunds(funds);

        setTestItem();

        Label curYearGrowth = getLabelFor(TranslationVars.CURRENT_YEAR);
        Label oneYearGrowth = getLabelFor(TranslationVars.ONE_YEAR);
        Label threeYearGrowth = getLabelFor(TranslationVars.THREE_YEARS);
        Label fiveYearGrowth = getLabelFor(TranslationVars.FIVE_YEARS);
        Label price = getLabelFor(TranslationVars.PRICE);
        assertEquals("posGrowth", threeYearGrowth.getStyleName());
        assertEquals("negGrowth", oneYearGrowth.getStyleName());
        assertEquals("posGrowth", curYearGrowth.getStyleName());
        assertEquals("negGrowth", fiveYearGrowth.getStyleName());
        assertEquals("price", price.getStyleName());
    }

    @Test
    public void deleteButtonExists() throws IOException, InvalidIsinException {
        displayFundsWithDeleteButtonAndTestItem();

        Button testButton = (Button) testItem.getItemProperty(
                translation.getTranslationFor(TranslationVars.DELETE))
                .getValue();

        assertEquals(EXPECTED_ISIN, testButton.getData());
    }

    @Test
    public void deleteButtonDeletesFund() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).deleteFund(anyString());
    }

    @Test
    public void deleteButtonClearsTheTable() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).removeFundTableItems();
    }

    @Test
    public void deleteButtonRefreshesFunds() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).showFundsWithDeleteButton();
    }

    // TODO check necessity
    @Test
    public void loginLayoutCanBeAccessed() {
        assertNotNull(view.getLoginLayout());
    }

    @Test
    public void loginIsHandledInPresenter() {
        view.handleLogin(anyString(), anyString());

        verify(handler, times(1)).handleLogin(anyString(), anyString());
    }

    @Test
    public void addFundHandledInPresenter() {
        view.handleAddFund(EXPECTED_ISIN);

        verify(handler, times(1)).addFund(any(Fund.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(1)).showFundsWithDeleteButton();
    }

    @Test
    public void loginFormCanBeRemoved() {
        view.removeLoginForm();

        verify(loginLayout, times(1)).removeLoginForm();
    }

    @Test
    public void fundsCanBeDisplayedWithDeleteButton() {
        displayFundsWithDeleteButtonAndTestItem();

        checkLabelContents();
        checkLabelTypes();
        checkType(Button.class, TranslationVars.DELETE);
    }

    @Test
    public void fundTableItemsCanBeRemoved() {
        view.displayFunds(funds);

        view.removeTableItems();

        assertEquals(0, view.getFundTable().getItemIds().size());
    }

    @Test
    public void isCorrectTemplateSet() {
        assertEquals(AllLayouts.DASHBOARD, view.getTemplateName());
    }
}
