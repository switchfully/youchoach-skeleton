(function ($) {
  $(document).ready(
    function () {
      console.log("test");
      $('.scrollspy').scrollSpy({
        scrollOffset: 0
      });
      $('.sidenav').sidenav();
      $('.parallax').parallax();
      $('select').formSelect();

    }); // end of document ready
})(jQuery); // end of jQuery name space
