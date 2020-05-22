(function($){
  $(function(){

    $('.scrollspy').scrollSpy({
      scrollOffset: 0
    });
    $('.sidenav').sidenav();
    $('.parallax').parallax();
    $('select').formSelect();

  }); // end of document ready

    jQuery(document).ready(function(){
      jQuery('.timepicker').timepicker({
        twelveHour: false,
        autoClose: true
      });
    });

})(jQuery); // end of jQuery name space


var currYear = (new Date()).getFullYear();
var currDate = (new Date());

$(document).ready(function() {
  $(".datepicker").datepicker({
    defaultDate: new Date(currDate),
    setDefaultDate: false,
    minDate: new Date(currDate),
    maxDate: new Date(currYear+1,12,31),
    yearRange: [currYear, currYear+1],
    format: "dd/mm/yyyy",
    autoClose: true
  });
});
