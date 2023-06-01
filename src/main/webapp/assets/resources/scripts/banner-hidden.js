function toggleCityPanel() {
    var cityPanel = document.getElementById('city-panel');
    // var cityName = document.querySelector('.city-name');
    var setaPraCima = document.getElementById('iconeSeta');
    if (cityPanel.style.display === 'none') {
        cityPanel.style.display = 'block';
        setaPraCima.classList.add('expanded');
    } else {
        cityPanel.style.display = 'none';
        setaPraCima.classList.remove('expanded');
    }
}