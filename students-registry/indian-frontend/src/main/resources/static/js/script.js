function longdate_to_string(longdate) {
    let date = new Date(longdate).toDateString();
    let month=["января","февраля","марта","апреля","мая","июня",
        "июля","августа","сентября","октября","ноября","декабря"];

    document.write(date.getDate()+ " " + month[date.getMonth()]
        + " " + date.getFullYear() + " г.");
}
