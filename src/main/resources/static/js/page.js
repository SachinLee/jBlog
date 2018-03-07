
function goPage(pageNum) {
    $("#searchForm").find("input[name='pageNum']").val(pageNum);
    $("#searchForm").submit();
}