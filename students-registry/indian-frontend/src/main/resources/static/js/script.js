function longdate_to_string(longdate) {
    let date = new Date(longdate).toDateString();
    document.getElementById("createdate").innerHTML = date;
}
