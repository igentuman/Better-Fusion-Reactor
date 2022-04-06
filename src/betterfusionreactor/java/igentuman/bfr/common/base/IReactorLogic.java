package igentuman.bfr.common.base;

public interface IReactorLogic<TYPE extends Enum<TYPE> & IReactorLogicMode<TYPE>> {

    TYPE getMode();

    TYPE[] getModes();
}
