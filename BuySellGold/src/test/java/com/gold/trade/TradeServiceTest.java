package com.gold.trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.gold.TradeApiCaller;
import org.gold.trade.TradeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TradeServiceTest {

  @Mock
  private TradeApiCaller tradeApiCaller;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testProfitScenario() {
    // given
    List<Integer> prices = Arrays.asList(5, 3, 8, 7, 10, 6);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    for (int i = 0; i < prices.size(); i++) {
      when(tradeApiCaller.getPriceOnDay(i)).thenReturn(prices.get(i));
    }
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    // then
    assertEquals(1, buyDay);  // Buy on day 1 (price 3)
    assertEquals(4, sellDay); // Sell on day 4 (price 10)
    assertTrue(sellDay > buyDay); // Ensure sell day is after buy day
  }

  @Test
  public void testNoProfitScenario() {
    // given
    List<Integer> prices = Arrays.asList(10, 9, 8, 7, 6, 5);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    for (int i = 0; i < prices.size(); i++) {
      when(tradeApiCaller.getPriceOnDay(i)).thenReturn(prices.get(i));
    }
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int sellDay = tradeService.getSellDay();

    // then
    assertTrue(sellDay == - 1); // Ensure sell is done the next day when no profit can be made
  }

  @Test
  public void testOneDayScenario() {
    // given
    List<Integer> prices = Arrays.asList(7);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    when(tradeApiCaller.getPriceOnDay(0)).thenReturn(prices.get(0));
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    // then
    assertTrue(sellDay == -1 && buyDay == 0); // Ensure sell is not possible when there is only one day
  }

  @Test
  public void testFlatPricesScenario() {
    // given
    List<Integer> prices = Arrays.asList(6, 6, 6, 6, 6, 6);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    for (int i = 0; i < prices.size(); i++) {
      when(tradeApiCaller.getPriceOnDay(i)).thenReturn(prices.get(i));
    }
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    // then
    assertEquals(0, buyDay);  // Buy on the first day
    assertEquals(1, sellDay); // Sell on the next day
    assertTrue(sellDay > buyDay); // Ensure sell day is after buy day
  }

  @Test
  public void testEqualPriceAfterPurchase() {
    // given
    List<Integer> prices = Arrays.asList(3, 5, 5, 5, 7, 10);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    for (int i = 0; i < prices.size(); i++) {
      when(tradeApiCaller.getPriceOnDay(i)).thenReturn(prices.get(i));
    }
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    // then
    assertEquals(0, buyDay);  // Buy on the first day (price 3)
    assertEquals(5, sellDay); // Sell on day 5 (price 10)
    assertTrue(sellDay > buyDay); // Ensure sell day is after buy day
  }

  @Test
  public void testMultipleEqualPrices() {
    // given
    List<Integer> prices = Arrays.asList(3, 6, 6, 6, 9, 12);
    when(tradeApiCaller.getNumberOfDays()).thenReturn(prices.size());
    for (int i = 0; i < prices.size(); i++) {
      when(tradeApiCaller.getPriceOnDay(i)).thenReturn(prices.get(i));
    }
    TradeService tradeService = new TradeService(tradeApiCaller);

    // when
    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    // then
    assertEquals(0, buyDay);  // Buy on day 0 (price 3)
    assertEquals(5, sellDay); // Sell on day 5 (price 12)
    assertTrue(sellDay > buyDay); // Ensure sell day is after buy day
  }
}
