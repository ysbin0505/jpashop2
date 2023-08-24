package jpabook.jpashop.web;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    //model.addAttribute("form", new AlbumForm());
    return "items/createItemForm";
  }


  @PostMapping("/items/new")
  public String create(BookForm form, AlbumForm albumForm) {

    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);
    return "redirect:/";


    /*if (albumForm != null) {
      Album album = new Album();
      album.setName(albumForm.getName());
      album.setPrice(albumForm.getPrice());
      album.setStockQuantity(albumForm.getStockQuantity());
      album.setArtist(albumForm.getArtist());
      album.setEtc(albumForm.getEtc());
      itemService.saveItem(album);
    }
    return "redirect:/";
  }*/
  }

  /**
   * 상품 목록 조회
   */
  @GetMapping(value = "/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";
  }
}

