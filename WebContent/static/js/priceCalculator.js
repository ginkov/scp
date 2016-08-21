/**
 * 自动计算商品的折扣和总价
 */

	function updateListPrice(id){
		var dbid = $('#prod'+id).val();
		$('#listprice'+id).val(Number(pricelist[dbid]).toFixed(2));
	}
	function updateDiscount(id){
		var listprice = $('#listprice'+id).val();
		var discountprice = $('#discountprice'+id).val();
		$('#discount'+id).val(Number(discountprice/listprice).toFixed(2));
	}
	function updateDiscountPrice(id){
		var listprice = $('#listprice'+id).val();
		var discount = $('#discount'+id).val();
		$('#discountprice'+id).val(Number(listprice*discount).toFixed(2));
	}
	function updateSubtotal(id){
		var qty = $('#qty'+id).val();
		var discountprice = $('#discountprice'+id).val();
		$('#subtotal'+id).val(Number(qty*discountprice).toFixed(2));
	}
	function setQty(id, v) {
		$('#qty'+id).val(v)
	}
	//=================
	function productChanged(id){
		updateListPrice(id);
		updateDiscountPrice(id);
		setQty(id,1);
		updateSubtotal(id);
	}
	function qtyChanged(id){
		updateListPrice(id);
		updateDiscountPrice(id);
		updateSubtotal(id);
	}
	function discountChanged(id){
		updateDiscountPrice(id);
		updateSubtotal(id);
	}
	function discountPriceChanged(id){
		updateDiscount(id);
		updateSubtotal(id);
	}