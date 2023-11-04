package lotto.controller;

import lotto.model.BonusNumber;
import lotto.model.GoalNumbers;
import lotto.service.InvestorService;
import lotto.view.input.InputView;
import lotto.view.output.OutputView;
import java.util.function.Supplier;

public class FactoryController {

    private final InputView inputView;
    private final OutputView outputView;

    public FactoryController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private <T> T createInstance(final Class<T> classType, final Supplier<T> creator) {
        T created = null;
        while (created == null) {
            created = tryGetInstance(creator, created);
        }
        return created;
    }

    private <T> T tryGetInstance(final Supplier<T> creator, T created) {
        try {
            created = creator.get();
        } catch (IllegalArgumentException exception) {
            outputView.printExceptionMessage(exception.getMessage());
        }
        return created;
    }

    public GoalNumbers createGoalNumbers() {
        return createInstance(GoalNumbers.class, () -> {
            outputView.askGoalNumbers();
            String goalNumbersInput = inputView.readLine();
            return GoalNumbers.from(goalNumbersInput);
        });
    }

    public BonusNumber createBonusNumber() {
        return createInstance(BonusNumber.class, () -> {
            outputView.askBonusNumber();
            String bonusNumberInput = inputView.readLine();
            return BonusNumber.from(bonusNumberInput);
        });
    }

    public InvestorService createInvestorService() {
        return createInstance(InvestorService.class, () -> {
            outputView.askInvestMoney();
            String investorInput = inputView.readLine();
            return InvestorService.createDefault(investorInput);
        });
    }
}
