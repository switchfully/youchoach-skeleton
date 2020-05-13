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
        twelveHour: false
      });
    });

})(jQuery); // end of jQuery name space


var currYear = (new Date()).getFullYear();

$(document).ready(function() {
  $(".datepicker").datepicker({
    //defaultDate: new Date(currYear,05,13),
    setDefaultDate: new Date(2020,05,14),
    maxDate: new Date(currYear+1,12,31),
    yearRange: [currYear, currYear+1],
    format: "yyyy/mm/dd"
  });
});
