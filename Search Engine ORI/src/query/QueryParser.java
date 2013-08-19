package query;

public class QueryParser
{
	private final String AND_OPERATOR = "&&";
	private final String OR_OPERATOR = "||";
	private final String NOT_OPERATOR = "!!";
	
	public void parseRawQuery(String rawQuery)
	{
		String[] rawQueryTokens = rawQuery.trim().split(" ");
		
	}
	
	private void categorizeRawQueryTokens(String[] rawTokens, String[] terms, String[] operators)
	{
		TreeNode root = new TreeNode(null);
		for (String token : rawTokens)
		{
			if (root.value == null)
			{
				root.value = token;
				continue;
			}
			if (root.left == null)
				root.left = new TreeNode(token);
			if (root.right == null)
				root.right = new TreeNode(token);
			
			
		}
	}
	
	private void insert(TreeNode root, String value)
	{
		
	}
}
