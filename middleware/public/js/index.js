$(document).ready(function () {
  FontAwesome();
  Lato('html, body');
  
  var years = $('#date_menu .year ul'),
      year = years.children('li');
  years.append(year.get().reverse());
  
  $('#date_menu .title').on('click', function () {
    if ($(this).next('ul').hasClass('closed')) {
      $('#date_menu ul.opened').switchClass('opened', 'closed').hide();
      $(this).next('ul').switchClass('closed', 'opened').show();
    } else {
      $(this).next('ul').switchClass('opened', 'closed').hide();
    }
  });
  $('#date_menu ul li').on('click', function () {
    $(this).parent('ul').switchClass('opened', 'closed').hide().parents('.drop').find('.title em').text($(this).text());
    /*
    var longMonths = ['Januray', 'March', 'May', 'July', 'August', 'October', 'December'],
        shortMonths = ['April', 'June', 'September', 'November'],
        months = $(this).text().split(' ');
    $.each(months, function(index, month) {
      if ($.inArray(month, longMonths) != -1) {
        $('#date_menu .day ul li').last().show();
      } else if ($.inArray(month, shortMonths) != -1) {
        $('#date_menu .day ul li').last().hide();
      } else if (months == 'February') {
        $('#date_menu .day ul li:gt(28)').hide();
      }
    });
    */
  });
});