**`getSellDay()`**: Finds the optimal day to sell after buying to maximize profit. The algorithm ensures that the selected buy and sell days lead to the best possible outcome, even if profit is not guaranteed.

## Running the Application
1. Compile the Java files and run the `Main` class.
2. The program will output:
   - A list of all generated prices.
   - The selected buy day and price.
   - The selected sell day and price.

## Example Output
```
Prices: [5, 3, 8, 7, 10, 6]
Buy day (1): 3
Sell day (4): 10
```
This output represents the decision to buy on day 1 (price 3) and sell on day 4 (price 10), with the goal of maximizing profit.

## Key Considerations
- The application is designed to select the optimal buy and sell days to achieve the highest possible profit. In scenarios where prices are continually declining, the application will decide not to make a purchase to avoid unnecessary losses.
- The `TradeApiCaller` is responsible for generating random prices, and thus the results may vary each time the program is run.

