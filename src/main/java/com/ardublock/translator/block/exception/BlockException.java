package com.ardublock.translator.block.exception;

public class BlockException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934239461972124783L;
	
	private Long blockId;
	private String message;
	
	public BlockException(Long blockId, String message)
	{
		this.blockId = blockId;
		this.message = message;
	}
	
	public Long getBlockId() {
		return blockId;
	}
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
