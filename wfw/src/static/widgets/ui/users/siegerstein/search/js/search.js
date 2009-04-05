function addSearchEvent(){
    $("#searchInputId").click(function(){
        $(this).removeClass('searchInputNormal').addClass('searchInputSelect').attr('value', '');
    });
    $("#searchInputId").blur(function(){
        $(this).removeClass('searchInputSelect').addClass('searchInputNormal').attr('value', 'Search');
    });
}
