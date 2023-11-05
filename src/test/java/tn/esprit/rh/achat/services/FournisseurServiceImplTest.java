package tn.esprit.rh.achat.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository; // Ajoutez l'import du repository correspondant

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FournisseurServiceImplTest {

    @Autowired
    @Qualifier("fournisseurServiceImpl")
    private IFournisseurService fournisseurService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private SecteurActiviteRepository secteurActiviteRepository; // Injectez le repository correspondant

    @Test
    public void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();

        // Créez un SecteurActivite
        SecteurActivite secteur1 = new SecteurActivite();
        secteur1.setLibelleSecteurActivite("Secteur 1");

        // Enregistrez le secteur1 dans la base de données
        secteur1 = secteurActiviteRepository.save(secteur1);

        // Utilisez un HashSet pour les secteurs
        Set<SecteurActivite> secteurs = new HashSet<>();
        secteurs.add(secteur1);

        // Associez les secteurs au fournisseur
        fournisseur.setSecteurActivites(secteurs);

        Fournisseur result = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(result);
    }

    @Test
    public void testDeleteFournisseur() {
        // Créez un fournisseur fictif
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setLibelle("Fournisseur de test"); // Utilisez setLibelle pour définir le libellé

        // Enregistrez le fournisseur dans la base de données
        fournisseur = fournisseurRepository.save(fournisseur);

        // Récupérez l'ID du fournisseur ajouté
        Long fournisseurId = fournisseur.getIdFournisseur();

        // Supprimez le fournisseur en utilisant le service
        fournisseurService.deleteFournisseur(fournisseurId);

        // Essayez de récupérer le fournisseur supprimé
        Fournisseur deletedFournisseur = fournisseurService.retrieveFournisseur(fournisseurId);

        // Vérifiez que le fournisseur a été supprimé et ne peut pas être récupéré
        assertNull(deletedFournisseur);
    }
    @Test
    public void testUpdateFournisseur() {
        // Créez un fournisseur fictif
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);

        // Enregistrez le fournisseur dans la base de données
        fournisseur = fournisseurService.addFournisseur(fournisseur);

        // Mettez à jour la catégorie du fournisseur
        fournisseur.setCategorieFournisseur(CategorieFournisseur.CONVENTIONNE);

        // Appelez la méthode de service pour mettre à jour le fournisseur
        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(fournisseur);

        // Assurez-vous que le fournisseur a été mis à jour correctement
        assertNotNull(updatedFournisseur);
        assertEquals(CategorieFournisseur.CONVENTIONNE, updatedFournisseur.getCategorieFournisseur());
    }

   /* @Test
    public void testRetrieveFournisseurWhenExists() {
        Long fournisseurId = 1L;
        Fournisseur expectedFournisseur = new Fournisseur();
        expectedFournisseur.setIdFournisseur(fournisseurId);

        // Ajoutez le fournisseur attendu au "repository"
        fournisseurRepository.save(expectedFournisseur);

        Fournisseur retrievedFournisseur = fournisseurService.retrieveFournisseur(fournisseurId);

        // Vérifiez que le fournisseur a été correctement récupéré
        assertNotNull(retrievedFournisseur);
        assertEquals(expectedFournisseur.getIdFournisseur(), retrievedFournisseur.getIdFournisseur());
    }*/
    
    @Test
    public void testRetrieveAllFournisseurs() {
        // Utilisez le service pour récupérer des fournisseurs (les données doivent être simulées)
        List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();

        // Effectuez des assertions sur les fournisseurs simulés
        assertNotNull(fournisseurs);
        // Assurez-vous que les données simulées correspondent à ce que vous attendez
    }

    @Test
    public void testSaveDetailFournisseur() {
        // Create a Fournisseur
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setLibelle("Fournisseur de test");

        // Create a DetailFournisseur
        DetailFournisseur df = new DetailFournisseur();
        df.setDateDebutCollaboration(new Date());

        // Set DetailFournisseur to Fournisseur
        fournisseur.setDetailFournisseur(df);

        // Save the Fournisseur along with the associated DetailFournisseur
        Fournisseur savedFournisseur = fournisseurService.addFournisseur(fournisseur);

        // Retrieve the DetailFournisseur from the saved Fournisseur
        DetailFournisseur savedDetailFournisseur = savedFournisseur.getDetailFournisseur();

        // Ensure that the DetailFournisseur is not null
        assert savedDetailFournisseur != null;
    }



}
