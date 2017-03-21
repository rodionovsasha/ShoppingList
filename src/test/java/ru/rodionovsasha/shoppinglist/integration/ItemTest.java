package ru.rodionovsasha.shoppinglist.integration;

/*
 * Copyright (©) 2016. Rodionov Alexander
 */

public class ItemTest extends BaseIntegrationTest {
/*    @Autowired
    private ItemService itemService;

    private MockMvc mockMvc;
    private Item item;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        item = itemService.getItemById(ITEM_ID);
    }

    @Test
    public void shouldGetItemTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/item?id=" + ITEM_ID).accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    public void shouldShowAddItemFormTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/item/add?listId=" + LIST_ID).accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("addItemForm"))
                //.andExpect(model().attributeExists(ITEM_FORM_NAME))
                .andExpect(model().attributeExists("listId"));
    }

    @Test
    public void shouldSaveItemTest() throws Exception {
        mockMvc.perform(post("/item/add")
                .param("name", "New item")
                .param("comment", "Item comment")
                .param("listId", String.valueOf(LIST_ID)))
                .andExpect(redirectedUrl("/itemsList?id=" + LIST_ID));
       // assertNotNull(itemService.findOneItemByName("New item"));
    }

    @Test
    public void shouldNotSaveItemTest() throws Exception {
        mockMvc.perform(post("/item/add")
                .param("name", "")
                .param("comment", "Item comment")
                .param("listId", String.valueOf(LIST_ID)))
                .andExpect(redirectedUrl("/item/add?listId=" + LIST_ID));
    }

    @Test
    public void shouldShowEditItemFormTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/item/edit?id=" + ITEM_ID + "&listId=" + LIST_ID).accept(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(view().name("editItem"));
                //.andExpect(model().attributeExists(EDIT_ITEM_FORM_NAME));
    }

    @Test
    public void shouldEditItemTest() throws Exception {
        mockMvc.perform(post("/item/edit")
                .param("id", String.valueOf(ITEM_ID))
                .param("name", "Edited name")
                .param("comment", "Edited comment")
                .param("listId", String.valueOf(LIST_ID)))
                .andExpect(redirectedUrl("/itemsList?id=" + LIST_ID));
        assertEquals("Edited name", item.getName());
        assertEquals("Edited comment", item.getComment());
    }

    @Test
    public void shouldNotSaveEditedItemTest() throws Exception {
        mockMvc.perform(post("/item/edit")
                .param("id", String.valueOf(ITEM_ID))
                .param("name", "")
                .param("comment", "Edited comment")
                .param("listId", String.valueOf(LIST_ID)))
                .andExpect(redirectedUrl("/item/edit?id=" + ITEM_ID));
        assertEquals(ITEM_NAME, item.getName());
        assertNotEquals("Edited comment", item.getComment());
    }

    @Test
    public void shouldToggleItemBoughtStatusTest() throws Exception {
        mockMvc.perform(get("/item/bought?id=" + ITEM_ID + "&listId=" + LIST_ID))
                .andExpect(status().is3xxRedirection());

        assertTrue(item.isBought());

        mockMvc.perform(get("/item/bought?id=" + ITEM_ID + "&listId=" + LIST_ID))
                .andExpect(status().is3xxRedirection());

        assertFalse(item.isBought());
    }

    @Test
    public void shouldDeleteItemTest() throws Exception {
        MediaType contentType = parseMediaType("text/html;charset=UTF-8");
        mockMvc.perform(get("/item/delete?id=" + ITEM_ID + "&listId=" + LIST_ID).accept(contentType))
                .andExpect(status().is3xxRedirection());
        assertNull(itemService.getItemById(ITEM_ID));
    }*/
}