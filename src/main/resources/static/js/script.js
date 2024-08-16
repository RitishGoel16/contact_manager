console.log("This is the script file");

const togglesidebar = () => {
    if ($('.sidebar').is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "2%");
    } else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
    }
};
