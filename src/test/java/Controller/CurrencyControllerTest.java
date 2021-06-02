package Controller;

import com.example.currencyconverter.controller.CurrencyController;
import com.example.currencyconverter.exceptions.ApiException;
import com.example.currencyconverter.exceptions.ApiRequestException;
import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.ExchangeRateService;
import com.example.currencyconverter.service.response.ExchangeQuery;
import com.example.currencyconverter.service.response.ExchangeResponse;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.*;

public class CurrencyControllerTest {

    @InjectMocks
    private CurrencyController controller;

    @Mock
    private ExchangeRateService exchangeRateService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testControllerWithSuccessfulResponse() {

        //ARRANGE
        Currency currency =  mock(Currency.class);
        when(currency.getSourceCurrency()).thenReturn("GBP");
        when(currency.getTargetCurrency()).thenReturn("USD");
        when(currency.getMonetaryValue()).thenReturn(10);

        ExchangeQuery exchangeQuery = mock(ExchangeQuery.class);
        when(exchangeQuery.getFrom()).thenReturn("GBP");
        when(exchangeQuery.getTo()).thenReturn("USD");
        when(exchangeQuery.getAmount()).thenReturn(10);

        ExchangeResponse exchangeResponse = mock(ExchangeResponse.class);
        when(exchangeResponse.getQuery()).thenReturn(exchangeQuery);
        when(exchangeResponse.getResult()).thenReturn((float)14.17);

        doReturn(null).when(exchangeRateService).getExistingCurrency(any());
        doReturn(exchangeResponse).when(exchangeRateService).getExchange(any());

        //ACT
        controller.getConvertedCurrency("GBP", "USD", 10);

        //ASSERT
        verify(exchangeRateService, times(1)).getExchange(any());
        verify(exchangeRateService, times(1)).getExchange(any());
    }

    @Test(expected = ApiRequestException.class)
    public void testControllerGetApiException() {



        Currency currency =  mock(Currency.class);
        when(currency.getSourceCurrency()).thenReturn("GBP");
        when(currency.getTargetCurrency()).thenReturn("USD");
        when(currency.getMonetaryValue()).thenReturn(10);

        ExchangeQuery exchangeQuery = mock(ExchangeQuery.class);
        when(exchangeQuery.getFrom()).thenReturn("GBP");
        when(exchangeQuery.getTo()).thenReturn("USD");
        when(exchangeQuery.getAmount()).thenReturn(10);

        ExchangeResponse exchangeResponse = mock(ExchangeResponse.class);
        when(exchangeResponse.getQuery()).thenReturn(exchangeQuery);
        when(exchangeResponse.getResult()).thenReturn((float)14.17);

        doReturn(null).when(exchangeRateService).getExistingCurrency(any());
        when(exchangeRateService.getExchange(any())).thenThrow(ApiRequestException.class);

        //Need to wrap in try catch to execute correctly
        try {
            controller.getConvertedCurrency("GBP", "USD", 10);
        } finally {
            verify(exchangeRateService, times(1)).getExchange(any());
            verify(exchangeRateService, times(1)).getExchange(any());
        }
    }









}
