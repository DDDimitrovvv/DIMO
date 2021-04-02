const storiesList = document.getElementById('storiesList');
const customErrorField = document.getElementById('customError');
const foundItems = document.getElementById('foundItems');
const dropDownMenu = document.getElementById('dpSelector');

const allStories = [];

fetch("http://localhost:8080/stories/api").then(response => response.json()).then(data => {
    for (let story of data) {
        allStories.push(story);
    }
})

const displayStories= (stories) => {
    let foundItemsCounter = 0;
    storiesList.innerHTML = stories
        .map((story) => {
            foundItemsCounter += 1;
            return `<div class="u-container-style u-image u-list-item u-repeater-item u-shading u-image-1"
                 data-image-width="500"
                 data-image-height="750"
                 data-animation-name="zoomIn"
                 data-animation-duration="1000"
                 data-animation-delay="0"
                 data-animation-direction="Down"
                 style="background-image:url(${story.imageUrl})">
                <a href="/stories/details/${story.id}">
                    <div class="u-container-layout u-similar-container u-container-layout-1">
                        <h5 class="u-align-center u-text u-text-1"
                            data-animation-name="fadeIn"
                            data-animation-duration="1000"
                            data-animation-delay="0"
                            data-animation-direction=""
                            style="background-color: purple;border-radius: 25px;">
                            ${story.storyTypeEnum}</h5>
                        <h5 class="u-text u-text-2"
                            style="color: black; white-space: pre-line; background-color: lavender; border: thin solid blueviolet"><b>Title:</b>
                        ${story.title}
                        
                        <b>Description:</b>
                        ${story.description.substring(0,50)}
                          </h5>
                    </div>
                </a>
            </div>`
        })
        .join('');

    foundItems.innerText = 'Found ' + foundItemsCounter + ' stories:';

}


function myFunction() {

    const selectedCategory = dropDownMenu.value.toLowerCase();
    console.log(selectedCategory);
    let filteredStories = allStories.filter(story => {
        console.log(story);
        console.log(story.storyTypeEnum.toLowerCase().includes(selectedCategory));

        return story.storyTypeEnum.toLowerCase().includes(selectedCategory);
    })

    if (filteredStories.length === 0) {
        customErrorField.style.display = "block";
        setTimeout(function () {
            $('#customError').fadeOut('fast');
        }, 1000);
    } else {
        foundItems.style.display = "block";
        setTimeout(function () {
            $('#foundItems').fadeOut('fast');
        }, 2000);
        displayStories(filteredStories);
    }
}


