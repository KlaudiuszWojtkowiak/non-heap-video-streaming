const form = document.querySelector('#video-form');
const videoDiv = document.querySelector('#video-player');
const videoScreen = document.querySelector('#video-screen');
const fileInput = document.querySelector('#file');
let fileName;
const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));

fetch('http://localhost:9000/movie/titles')
    .then(result => result.json())
    .then(result => {

        const videos = document.querySelector('#your-videos');
        if (result.length > 0) {
            for (let vid of result) {
                const li = document.createElement('LI');
                const link = document.createElement('A');
                link.innerText = vid;
                link.href = window.location.origin + window.location.pathname + '?video=' + vid;
                li.appendChild(link);
                videos.appendChild(li);
            }
        } else {
            videos.innerHTML = 'Nothing has been found';
        }

    });

if (queryParams.video) {
    videoScreen.src = `http://localhost:9000/movie/${queryParams.video}`;
    videoDiv.style.display = 'block';
    document.querySelector('#now-playing')
        .innerText = 'Now playing ' + queryParams.video;
}


fileInput.addEventListener('change',ev=>{
    fileName=ev.target.files[0].name;

    ev.preventDefault();
    let data = new FormData(form);
    data.set("name",fileName)
    fetch('http://localhost:9000/movie', {
        method: 'POST',
        body: data
    }).then(result => result.text()).then(_ => {
        window.location.reload();
    });
})


;
